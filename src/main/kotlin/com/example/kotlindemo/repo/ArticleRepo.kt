package com.example.kotlindemo.repo

import com.example.kotlindemo.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepo : JpaRepository<Article, Long> {
}