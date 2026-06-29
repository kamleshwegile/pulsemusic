import re

with open('pulse-backend-python/main.py', 'r', encoding='utf-8') as f:
    code = f.read()

# Replace find_one({"email": user.email}) in login
code = code.replace(
    'db_user = users_collection.find_one({"email": user.email})',
    'db_user = users_collection.find_one({"email": {"$regex": f"^{user.email.strip()}$", "$options": "i"}})'
)

# Replace find_one({"email": user.email}) in register
code = code.replace(
    'if users_collection.find_one({"email": user.email}):',
    'if users_collection.find_one({"email": {"$regex": f"^{user.email.strip()}$", "$options": "i"}}):'
)

# Replace find_one({"email": req.email}) in forgot_password
code = code.replace(
    'db_user = users_collection.find_one({"email": req.email})',
    'db_user = users_collection.find_one({"email": {"$regex": f"^{req.email.strip()}$", "$options": "i"}})'
)

# And in verify_code and reset_password, we also need to match the reset_code collection robustly
# But for users_collection in reset_password:
code = code.replace(
    'users_collection.update_one({"email": req.email}, {"$set": {"passwordHash": hashed_pw}})',
    'users_collection.update_one({"email": {"$regex": f"^{req.email.strip()}$", "$options": "i"}}, {"$set": {"passwordHash": hashed_pw}})'
)

code = code.replace(
    'reset_codes_collection.update_one(\n        {"email": req.email},',
    'reset_codes_collection.update_one(\n        {"email": req.email.strip().lower()},'
)

code = code.replace(
    'record = reset_codes_collection.find_one({"email": req.email, "code": req.code})',
    'record = reset_codes_collection.find_one({"email": req.email.strip().lower(), "code": req.code})'
)
code = code.replace(
    'reset_codes_collection.delete_one({"email": req.email})',
    'reset_codes_collection.delete_one({"email": req.email.strip().lower()})'
)

# Also ensure insert_one in register saves it stripped
code = code.replace(
    'users_collection.insert_one({"username": user.username, "email": user.email, "passwordHash": hashed_pw})',
    'users_collection.insert_one({"username": user.username, "email": user.email.strip(), "passwordHash": hashed_pw})'
)


with open('pulse-backend-python/main.py', 'w', encoding='utf-8') as f:
    f.write(code)
