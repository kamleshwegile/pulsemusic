import requests
TOKEN = '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'
headers = {
    'Authorization': f'token {TOKEN}',
    'Accept': 'application/vnd.github.v3+json'
}
print(requests.get('https://api.github.com/repos/RAHUL-0568/Pulse-Music-Releases', headers=headers).json())
