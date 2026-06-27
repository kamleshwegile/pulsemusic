import asyncio
import websockets

async def test():
    try:
        async with websockets.connect('wss://api.pulsemusic.app/api/v1/jam/ws/ROOM123/guest') as ws:
            print('SUCCESS!')
            return
    except Exception as e:
        print('Failed:', e)

asyncio.run(test())
