import boto3
from botocore.exceptions import ClientError

import os

def upload_file_to_s3(file_path, bucket_name):
    s3_client = boto3.client('s3')
    object_name = os.path.basename(file_path)
    try:
        # Upload the file
        s3_client.upload_file(
            Filename=file_path,
            Bucket=bucket_name,
            Key=object_name
        )
        print("File uploaded successfully.")
    except ClientError as e:
        print(f"Upload failed: {e}")
        return False

    import time
    # Sleep to allow S3 eventual consistency
    time.sleep(5)
    # Validate the file exists
    try:
        response = s3_client.head_object(Bucket=bucket_name, Key=object_name)
        print("File exists in S3.")
    except ClientError as e:
        error_code = e.response['Error']['Code']
        if error_code == '404':
            print(f"File not found in S3: {object_name}")
            return False
        else:
            print(f"ClientError during head_object: {error_code} - {e}")
            return False

    # Validate the tag
    try:
        tags = {"bucketav": "clean"} # The Tag is expected to be set by bucketAV
        tag_response = s3_client.get_object_tagging(Bucket=bucket_name, Key=object_name)
        tag_set = {tag['Key']: tag['Value'] for tag in tag_response['TagSet']}
        for k, v in tags.items():
            if tag_set.get(k) != v:
                print(f"Tag {k} does not match expected value.")
                return False
        print("Tag validation successful.")
        return True
    except ClientError as e:
        print(f"Tag validation failed: {e}")
        return False

# Example usage
if __name__ == "__main__":
    file_path = "/Users/vjayachandran/code/yava/eicar.com"
    bucket_name = "vinod-static-react"
    upload_file_to_s3(file_path, bucket_name)