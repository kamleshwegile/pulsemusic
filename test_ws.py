import asyncio
import websockets

async def test():
    try:
        async with websockets.connect('wss://pulse-music-backend.onrender.com/api/v1/jam/ws/ROOM123/guest') as ws:
            print('Connected!')
            res = await ws.recv()
            print('Received:', res)
    except Exception as e:
        print('Error:', e)

asyncio.run(test())
