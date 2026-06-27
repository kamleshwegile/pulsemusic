import requests

TOKEN = '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'
headers = {
    'Authorization': f'token {TOKEN}',
    'Accept': 'application/vnd.github.v3+json'
}

data = {
    "name": "Pulse-Music-Releases",
    "description": "Public repository for Pulse Music Android APK updates",
    "private": False
}

res = requests.post('https://api.github.com/user/repos', headers=headers, json=data)
print(res.status_code)
print(res.json())
