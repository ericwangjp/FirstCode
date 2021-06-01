package com.example.firstcode

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: DSL
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/6/1 9:57 上午
 *
 *
 */
class Dependency {
    val libraries = ArrayList<String>()

    fun implementation(lib: String) {
        libraries.add(lib)
    }

    fun dependencies(block: Dependency.() -> Unit): List<String> {
        val dependency = Dependency()
        dependency.block()
        return dependency.libraries
    }
}