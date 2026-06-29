import re

with open('pulse-backend-python/main.py', 'r', encoding='utf-8') as f:
    code = f.read()

# Add the filter function if not exists
if 'def filter_jio_image' not in code:
    filter_func = '''
def filter_jio_image(url):
    if url and isinstance(url, str) and "default" in url and (".png" in url or ".jpg" in url):
        return ""
    return url

'''
    # Put it right after the imports
    parts = code.split('\n\n', 1)
    code = parts[0] + '\n\n' + filter_func + parts[1]

# Now let's do safe replacements for all the image extraction patterns
patterns_to_replace = [
    (r'(cover\s*=\s*images\[-1\]\["url"\] if images else "")', r'cover = filter_jio_image(images[-1]["url"] if images else "")'),
    (r'(artist_image\s*=\s*images\[-1\]\.get\("url", artist_image\))', r'artist_image = filter_jio_image(images[-1].get("url", artist_image))'),
    (r'(alb_cover\s*=\s*alb_images\[-1\]\["url"\] if isinstance\(alb_images, list\) and alb_images else "")', r'alb_cover = filter_jio_image(alb_images[-1]["url"] if isinstance(alb_images, list) and alb_images else "")'),
    (r'(sim_cover\s*=\s*sim_images\[-1\]\["url"\] if isinstance\(sim_images, list\) and sim_images else "")', r'sim_cover = filter_jio_image(sim_images[-1]["url"] if isinstance(sim_images, list) and sim_images else "")'),
    (r'(ts_cover\s*=\s*ts_images\[-1\]\["url"\] if isinstance\(ts_images, list\) and ts_images else "")', r'ts_cover = filter_jio_image(ts_images[-1]["url"] if isinstance(ts_images, list) and ts_images else "")'),
    (r'(artist_image\s*=\s*images\[-1\]\["url"\] if images else "")', r'artist_image = filter_jio_image(images[-1]["url"] if images else "")')
]

for old, new in patterns_to_replace:
    code = re.sub(old, new, code)

# Clean up the manual filters we added earlier so we don't have redundant checks
code = re.sub(r'\s*if "default" in cover and \("\.png" in cover or "\.jpg" in cover\):\s*cover = ""', '', code)
code = re.sub(r'\s*if "default" in cover and "\.png" in cover:\s*cover = ""', '', code)

with open('pulse-backend-python/main.py', 'w', encoding='utf-8') as f:
    f.write(code)
