import asyncio
import websockets
import uuid

async def test():
    try:
        url = f'ws://127.0.0.1:8080/api/v1/jam/ws/ROOM123/{uuid.uuid4()}'
        async with websockets.connect(url) as ws:
            print('SUCCESS!')
            return
    except Exception as e:
        print('Failed:', e)

asyncio.run(test())
