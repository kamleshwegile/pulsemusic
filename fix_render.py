import requests

API_KEY = 'rnd_UF4cLK845MZOmtGpYcrTNwmw6lzl'
HEADERS = {
    'Accept': 'application/json',
    'Authorization': f'Bearer {API_KEY}'
}

res = requests.get('https://api.render.com/v1/services', headers=HEADERS)
services = res.json()

target_services = []
for s in services:
    srv = s['service']
    name = srv['name']
    if name in ['pulse-music-backend-fallback-1', 'pulse-music-backend-fallback-2']:
        target_services.append(srv['id'])

for ts in target_services:
    res = requests.post(f'https://api.render.com/v1/services/{ts}/deploys', headers=HEADERS)
    print(f'Deploy {ts}: {res.status_code}')

