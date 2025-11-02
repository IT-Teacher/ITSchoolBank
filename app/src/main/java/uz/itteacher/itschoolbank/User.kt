package uz.itteacher.itschoolbank

class User(
    var userId: Int,
    var userFullName: String,
    var userImage: Int
){
    constructor() : this(0, "", 0)
}