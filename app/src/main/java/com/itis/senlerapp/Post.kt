package com.itis.senlerapp

import android.net.Uri

data class Post(
    val id: Int, val text: String, val date: Long, val vkState: Boolean,
    val vkGroupState: Boolean, val tgState: Boolean, val instState: Boolean,
    val photos: MutableList<Uri>
) {
    override fun toString(): String {
        return "Post(id=$id, text='$text', date=$date, vkState=$vkState, vkGroupState=$vkGroupState, tgState=$tgState, instState=$instState, photos=$photos)"
    }
}