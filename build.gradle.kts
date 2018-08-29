buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.1.4")

        //Kotlin编译的插件
        //http://kotlinlang.org/docs/reference/using-gradle.html
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.50")
    }
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}