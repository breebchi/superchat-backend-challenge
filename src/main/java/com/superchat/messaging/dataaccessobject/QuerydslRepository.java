package com.superchat.messaging.dataaccessobject;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.List;

@NoRepositoryBean
public interface QuerydslRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T>
{

    @Override
    @NonNull
    List<T> findAll(@NonNull Predicate predicate);
}
