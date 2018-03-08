package com.example.kotlindemo

import com.example.kotlindemo.controller.ArticleController
import com.example.kotlindemo.model.Article
import com.example.kotlindemo.repo.ArticleRepo
import org.assertj.core.api.Assertions
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders


//@RunWith(SpringRunner::class)
@RunWith(MockitoJUnitRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class KotlinDemoApplicationTests {

    @InjectMocks
    val controller: ArticleController? = null

    private var mockMvc: MockMvc? = null

    @Mock
    private val articlerepo: ArticleRepo? = null


    @Test
    fun contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build()
    }

    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    private fun setupArticles() {
        val article1 = Article(title = "title1", content = "content")
        val article2 = Article(title = "title1", content = "content")

        val articles = mutableListOf<Article>()
        articles.add(article1)
        articles.add(article2)
  //      `when`(articlerepo.findAll(any(kotlin.collections.<Article>::class.java))).thenReturn(Customer())
        doReturn(articles).`when`(articlerepo)?.findAll()
    }

    @Test
    fun shouldReturnDefaultMessageForApiArticles() {
        setupArticles()
        mockMvc!!.perform(get("/api/articles")).andExpect(status().isOk)
                .andDo(print()).andExpect(content().string(CoreMatchers.containsString("[]")))
    }

    @Test
    fun shouldpostAnArticleToarticles(){
        val i: Long = 1
        val article1 = Article(id = i,title = "title1", content = "content")
        `when`(articlerepo?.save(Matchers.any(Article::class.java))).thenReturn(article1)
        //doReturn(article1).`when`(articlerepo)?.save(article1)
        this.mockMvc!!.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(article1)))
                .andDo(print())
                .andExpect(status().isConflict())
                //     .andExpect(status().isOk).andDo(print())
                //.andExpect(jsonPath("$.title", CoreMatchers.equalTo("title1")))
    }

    @Test(expected = RuntimeException::class)
    fun shouldReturnArticleDetailsWhengetArticleById() {
        val i: Long = 1
        val article = Article(title = "title1", content = "content")
        doReturn(article).`when`(articlerepo)?.getOne(i)

        mockMvc!!.perform(get("/api/articles/{id}",i))
                .andDo(print())
                .andExpect(content().string(CoreMatchers.not(null)))
                .andExpect(status().`is`(200))
                .andExpect(status().isOk)
    }
    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Test(expected = RuntimeException::class)
    fun shouldReturnArticleDetailsWhenupdateArticleById() {
        val article = Article(title = "title1", content = "content")
        val i: Long = 1
//        doReturn(article).`when`(articlerepo)?.getOne(i)
        `when`(articlerepo?.getOne(Matchers.anyLong())).thenReturn(article)

        val updatearticle = Article(id=1,title = "title2", content = "content2")
        this.mockMvc!!.perform(put("/api/articles/{id}",i)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatearticle)))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().string(("title2")))
    }

    @Test(expected = RuntimeException::class)
    fun shouldReturnFalseArticleDetailsWhenupdateArticleById() {
        val i: Long = 1
        val article = Article(title = "title1", content = "content")

        Mockito.doReturn(article).`when`(articlerepo)?.getOne(i)
        //  Mockito.doReturn(1).`when`(articlerepo)?.save(article)
        val updatearticle = Article(title = "title2", content = "content2")

        this.mockMvc!!.perform(put("/articles/{id}",1).content(asJsonString(updatearticle))).andExpect(status().isOk)
                .andDo(print()).andExpect(content().string(("title1")))
    }

    @Test(expected = RuntimeException::class)
    fun shouldDeleteArticleDetailsWhendeleteArticleById() {
        val i: Long = 1
        val article = Article(title = "title1", content = "content")

        Mockito.doReturn(article).`when`(articlerepo)?.getOne(i)
        //  Mockito.doReturn(1).`when`(articlerepo)?.save(article)
        val delete = Article(title = "title2", content = "content2")

        this.mockMvc!!.perform(delete("/articles/{id}",1)).andExpect(status().isOk)
                .andDo(print()).andExpect(content().string(("title1")))
    }


    fun asJsonString(obj: Any): String {
        try {
            return ObjectMapper().writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}

