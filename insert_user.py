import pymongo
import bcrypt
import time
from bson import ObjectId

import os

client = pymongo.MongoClient(os.getenv("MONGO_URL", ""))

db = client["pulse"]
users = db["users"]

email = "testuser@example.com"
password = "testpassword123"

hashed = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')

user_doc = {
    "_id": str(ObjectId()),
    "username": "testuser",
    "email": email,
    "passwordHash": hashed,
    "createdAt": int(time.time() * 1000)
}

result = users.insert_one(user_doc)
print(f"Inserted user with _id: {result.inserted_id}")
print(f"Email: {email}")
print(f"Password: {password}")
