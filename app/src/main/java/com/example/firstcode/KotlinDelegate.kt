package com.example.firstcode

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: KotlinDelegate kotlin 委托(类委托、属性委托、局部变量委托)
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/6/9 9:36 上午
 *
 */

/**
 * 类委托
 * 语法格式:
 * class <类名>(b : <基础接口>) : <基础接口> by <基础对象>
 */

// 基础接口
interface Base {
    fun print()
}

// 基础对象
class BaseImpl(val x: Int) : Base {
    override fun print() {
        println("hello $x")
    }
}

class DelegateObj(b: Base) : Base by b

//fun main() {
//    val b = BaseImpl(110)
//    DelegateObj(b).print()
//}

/**
 * 属性委托
 * 语法格式:
 * val/var <属性名> : <类型> by <基础对象>
 */
class PropTest {
    var prop: String by DelegateProp()
}

class DelegateProp {
    operator fun getValue(propTest: PropTest, property: KProperty<*>): String {
        println("getValue")
        return _value
    }

    operator fun setValue(propTest: PropTest, property: KProperty<*>, s: String) {
        println("setValue")
        _value = s
    }

    private var _value: String = "aaa"
}

//fun main() {
//    val propTest = PropTest()
//    println(propTest.prop)
//    propTest.prop = "bbb"
//    println(propTest.prop)
//}

/**
 * 局部变量委托
 * 延迟属性委托 lazy
 * 语法格式:
 * val/var <属性名> : <类型> by lazy
 */
//fun main() {
//    val lazyValue: String by lazy {
//        println("lazy init completed")
//        "hello lazy"
//    }
//
//    println("开始执行")
//    println(lazyValue)
//    println(lazyValue)
//
////    输出
////      开始执行
////      lazy init completed
////      hello lazy
////      hello lazy
//}

/**
 * 可观察属性 ObservableProperty
 */
class UserClass {
    var name: String by Delegates.observable("初始值") { prop, old, new ->
        println("属性值：$prop，旧值：$old，新值：$new")
    }
}

//fun main() {
//    val user = UserClass()
//    println(user.name)
//    user.name = "第一次赋值"
//    println(user.name)
//    user.name = "第二次赋值"
//    println(user.name)
//}

/**
 * 使用 Map 存储属性值
 * Map / MutableMap 也可以用来实现属性委托，从而此时字段名是 Key，属性值是 Value
 * 如果 Map 中不存在委托属性名的映射值，在取值的时候会抛异常：Key $key is missing in the map.
 */
class UserTwo(val map: Map<String, Any?>) {
    val name: String by map
}

//fun main() {
//    val map = mutableMapOf("name" to "kotlin")
////    val map = mutableMapOf<String,Any?>()
//    val userTwo = UserTwo(map)
//    println(userTwo.name)
//    map["name"] = "android"
//    println(userTwo.name)
//}

/**
 * ReadOnlyProperty / ReadWriteProperty 接口
 */

val num :Int by Delegates.notNull()

val name by ReadOnlyProperty<Any?, String> { thisRef, property -> "cc" }

var names by object : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "dd"
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    }

}
