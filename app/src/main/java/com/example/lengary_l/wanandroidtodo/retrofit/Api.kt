package com.example.lengary_l.wanandroidtodo.retrofit

/**
 * Created by CoderLengary
 */
class Api private constructor() {

    companion object {

        const val BASE_URL = "http://www.wanandroid.com/"

        const val LOGIN = BASE_URL + "user/login/"

        const val REGISTER = BASE_URL + "user/register/"

        const val TODO_BASE = BASE_URL + "lg/todo/"

        const val GET_ALL_TODO = TODO_BASE + "list/"

        const val ADD_TODO = TODO_BASE + "add/json"

        const val UPDATE_TODO = TODO_BASE + "update/"

        const val DELETE_TODO = TODO_BASE + "delete/"

        const val UPDATE_DONE_TODO = TODO_BASE + "done/"

        const val GET_UNDO_TODO = TODO_BASE + "listnotdo/"

        const val GET_DONE_TODO = TODO_BASE + "listdone/"
    }

}