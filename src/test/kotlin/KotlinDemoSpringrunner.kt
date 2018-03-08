package com.example.kotlindemo
import com.example.kotlindemo.model.Article
import com.example.kotlindemo.repo.ArticleRepo
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@DataJpaTest
class KotlinDemoSpringrunner {
    @Autowired
    private val entityManager: TestEntityManager? = null
    @Autowired
    private val articlerepo: ArticleRepo? = null

    @Test
    fun findallArticles() {
        // given
        val i: Long = 1
        val article1 = Article(i, title = "title1", content = "content")
        val article2 = Article(i, title = "title2", content = "content2")
        entityManager?.persist(article1)
        entityManager?.persist(article2)
        entityManager?.flush()

        // when
        val articles = articlerepo?.findAll()
        println(articles?.size)

        // then
        assertEquals(articles?.get(0)?.title,"title1");
    }
}