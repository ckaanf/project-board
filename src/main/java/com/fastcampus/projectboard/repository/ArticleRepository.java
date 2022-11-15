package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, //검색기능
        QuerydslBinderCustomizer<QArticle>
{
    @Override
    default void customize(QuerydslBindings bindings, QArticle root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title,root.content, root.hashtag, root.createdAt, root.createdBy);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like ''
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like ''
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase); // like ''
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // like ''
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like ''
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '%${v}$%'
    }
}