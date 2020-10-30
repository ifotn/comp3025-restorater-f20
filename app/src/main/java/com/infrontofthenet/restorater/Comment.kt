package com.infrontofthenet.restorater

class Comment {
    var commentId: String? = null
    var username: String? = null
    var body: String? = null
    var restaurantId: String? = null

    constructor() {}

    constructor(commentId: String, username: String, body: String, restaurantId: String) {
        this.commentId = commentId
        this.username = username
        this.body = body
        this.restaurantId = restaurantId
    }
}