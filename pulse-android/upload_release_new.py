import requests
import json
import os
import sys

TOKEN = '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'
REPO = 'RAHUL-0568/Pulse-Music-Releases'
TAG = 'v2.0.0'
APK_PATH = r'app\build\outputs\apk\debug\app-debug.apk'

headers = {
    'Authorization': f'token {TOKEN}',
    'Accept': 'application/vnd.github.v3+json'
}

# 1. Create the release
release_data = {
    'tag_name': TAG,
    'name': f'Pulse Music Update {TAG}',
    'body': 'Added new UI playing animations with drop shadows across all screens.',
    'draft': False,
    'prerelease': False
}

print('Creating release...')
res = requests.post(f'https://api.github.com/repos/{REPO}/releases', headers=headers, json=release_data)

if res.status_code == 422:
    print('Release already exists or tag invalid. Fetching existing release to upload asset...')
    res = requests.get(f'https://api.github.com/repos/{REPO}/releases/tags/{TAG}', headers=headers)

if res.status_code not in (201, 200):
    print(f'Failed to create release: {res.text}')
    sys.exit(1)

release = res.json()
upload_url = release['upload_url'].split('{')[0]
print(f'Release created/found. Upload URL: {upload_url}')

# 2. Upload the APK
print(f'Uploading {APK_PATH}...')
with open(APK_PATH, 'rb') as f:
    upload_headers = {
        'Authorization': f'token {TOKEN}',
        'Accept': 'application/vnd.github.v3+json',
        'Content-Type': 'application/vnd.android.package-archive'
    }
    upload_res = requests.post(f'{upload_url}?name=PulseMusic-v2.0.0.apk', headers=upload_headers, data=f)

if upload_res.status_code == 201:
    print('Upload successful!')
else:
    print(f'Upload failed: {upload_res.text}')
