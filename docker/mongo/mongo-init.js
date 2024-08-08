db.getSiblingDB('taxicab').createUser(
        {
            user: "taxicabUser",
            pwd: "PosLLy22pO7T4i",
            roles: [
                {
                    role: "readWrite",
                    db: "taxicab"
                }
            ]
        }
);