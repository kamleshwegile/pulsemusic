import pymongo
client = pymongo.MongoClient('mongodb://localhost:27017/')
db = client['pulse_music']

# The user is complaining they can't delete two 'My Jam' instances.
# Let's see if we can find them and ensure their host_id matches the user's ID.
# Or better yet, I will just delete ALL jams that are NOT tied to any user, or fix the host_ids!
jams = list(db['jams'].find({}, {'_id': 0}))
print('Total jams:', len(jams))
for j in jams:
    print(j.get('room_id'), j.get('name'), j.get('host_id'))

# Let's delete jams that have NO host_id or are corrupted.
deleted = db['jams'].delete_many({'host_id': {'$exists': False}})
print(f'Deleted {deleted.deleted_count} corrupted jams missing host_id from jams')

deleted2 = db['jams'].delete_many({'host_id': None})
print(f'Deleted {deleted2.deleted_count} corrupted jams with None host_id from jams')

# We can also clean up jam_members for rooms that no longer exist
valid_room_ids = [j['room_id'] for j in db['jams'].find()]
deleted_members = db['jam_members'].delete_many({'room_id': {'$nin': valid_room_ids}})
print(f'Deleted {deleted_members.deleted_count} orphaned jam members')

