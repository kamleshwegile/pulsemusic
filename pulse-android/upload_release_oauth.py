import requests
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

print('Creating release with OAuth token...')
res = requests.post(f'https://api.github.com/repos/{REPO}/releases', headers=headers, json=release_data)

if res.status_code == 422:
    print('Release already exists. Fetching...')
    res = requests.get(f'https://api.github.com/repos/{REPO}/releases/tags/{TAG}', headers=headers)

if res.status_code not in (201, 200):
    print(f'Failed: {res.text}')
    sys.exit(1)

release = res.json()
upload_url = release['upload_url'].split('{')[0]

# 2. Upload the APK
print('Uploading APK...')
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
