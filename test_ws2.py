import asyncio
import websockets

paths = [
    '/api/v1/jam/ws/ROOM123/guest',
    '/api/v1/jam/ws/ROOM123/guest?action=create',
    '/ws/jam/ROOM123/guest',
    '/api/jam/ws/ROOM123/guest',
    '/api/v1/ws/jam/ROOM123/guest',
]

async def test():
    for p in paths:
        url = 'wss://pulse-music-backend.onrender.com' + p
        try:
            async with websockets.connect(url) as ws:
                print('SUCCESS:', url)
                return
        except Exception as e:
            print('Failed:', url, '->', e)

asyncio.run(test())
