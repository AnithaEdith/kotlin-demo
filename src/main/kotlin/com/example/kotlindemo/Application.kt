package com.example.kotlindemo
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan
//@Configuration
@ComponentScan
open class Application
/*

@Bean
fun controller() = ArticleController()
*/

fun main(args: Array<String>) {

    SpringApplication.run(Application::class.java, *args)
}