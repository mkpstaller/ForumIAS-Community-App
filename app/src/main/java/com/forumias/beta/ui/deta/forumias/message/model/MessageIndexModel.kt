package com.forumias.beta.ui.deta.forumias.message.model

data class MessageIndexModel(
    val message: String,
    val result: List<Result>,
    val status: String
)


data class Result(
        val chat_id: Int,
        val created_at: String,
        val file: String,
        val file_extension: String,
        val frm_name: String,
        val frm_u_img: String,
        val frm_verified: Int,
        val from_isolated: Int,
        val from_user: Int,
        val id: Int,
        val ip_address: String,
        val message: String,
        val msg_type: Int,
        val notification_flag: Int,
        val read_flag: Int,
        val status: Int,
        val to_isolated: Int,
        val to_name: String,
        val to_u_img: String,
        val to_user: Int,
        val to_verified: Int,
        val updated_at: String
)