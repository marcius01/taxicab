db.createCollection("User");
db.User.insertMany([
    { username: "user1", password: "0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e", roles: ["admin"] },
    { username: "user2", password: "6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4", roles: ["user"] }
]);