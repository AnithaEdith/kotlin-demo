package com.example.kotlindemo.controller

import com.example.kotlindemo.model.Article
import com.example.kotlindemo.repo.ArticleRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ArticleController(private val articleRepository: ArticleRepo) {

    @GetMapping("/articles")
    fun getAllArticles(): List<Article> =
            articleRepository.findAll()


    @PostMapping("/articles")
    fun createNewArticle(@Valid @RequestBody article: Article): Article = articleRepository.save(article)

    @GetMapping("/articles/{id}")
    fun getArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Article> {
        println("inside code getArticleById")
        val one = articleRepository.getOne(articleId)
        println("fetched article" )
        return ResponseEntity.ok().body(one)
    }

    @PutMapping("/articles/{id}")
    fun updateArticleById(@PathVariable(value = "id") articleId: Long,
                          @Valid @RequestBody newArticle: Article): ResponseEntity<Article> {
        val existingArticle = articleRepository.getOne(articleId)
        println("new article details " + newArticle.title)
        val updatedArticle: Article = existingArticle
                .copy(title = newArticle.title, content = newArticle.content)
        return ResponseEntity.ok().body(articleRepository.save(updatedArticle))
    }

    @DeleteMapping("/articles/{id}")
    fun deleteArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> {
        val article = articleRepository.getOne(articleId)
        articleRepository.delete(article)
        return ResponseEntity<Void>(HttpStatus.OK)
    }
}
