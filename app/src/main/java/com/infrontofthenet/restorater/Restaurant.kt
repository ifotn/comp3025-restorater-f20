package com.infrontofthenet.restorater

class Restaurant {
    // declare class properties
    var id: String? = null
    var name: String? = null
    var rating: Int? = null

    // empty constructor - this is required!
    constructor() {}

    constructor(id: String, name: String, rating: Int) {
        this.id = id
        this.name = name
        this.rating = rating
    }
}