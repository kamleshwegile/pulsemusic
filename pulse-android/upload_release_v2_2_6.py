import requests
import json
import os
import subprocess

def get_github_oauth_token():
    try:
        result = subprocess.run(['git', 'credential', 'fill'], input=b"protocol=https\nhost=github.com\n\n", capture_output=True, check=True)
        output = result.stdout.decode('utf-8')
        for line in output.split('\n'):
            if line.startswith('password='):
                return line.split('=', 1)[1].strip()
    except Exception as e:
        print(f"Error getting token: {e}")
    return None

token = get_github_oauth_token()

owner = "RAHUL-0568"
repo = "Pulse-Music-Releases"

print("Creating release with OAuth token...")
create_url = f"https://api.github.com/repos/{owner}/{repo}/releases"
headers = {
    "Authorization": f"token {token}",
    "Accept": "application/vnd.github.v3+json"
}
data = {
    "tag_name": "v2.2.6",
    "target_commitish": "main",
    "name": "Pulse Music Update v2.2.6",
    "body": "Curvy UI in Library, Minimal Profile UI, fetching real email securely.",
    "draft": False,
    "prerelease": False
}

response = requests.post(create_url, headers=headers, json=data)
if response.status_code == 201:
    release_data = response.json()
    upload_url = release_data['upload_url'].split('{')[0]
    
    print("Uploading APK...")
    apk_path = "app/build/outputs/apk/debug/app-debug.apk"
    with open(apk_path, "rb") as f:
        apk_data = f.read()
    
    upload_headers = {
        "Authorization": f"token {token}",
        "Content-Type": "application/vnd.android.package-archive"
    }
    upload_params = {"name": "PulseMusic-v2.2.6.apk"}
    upload_response = requests.post(upload_url, headers=upload_headers, params=upload_params, data=apk_data)
    
    if upload_response.status_code == 201:
        print("Upload successful!")
    else:
        print(f"Failed to upload APK: {upload_response.text}")
else:
    print(f"Failed to create release: {response.text}")
