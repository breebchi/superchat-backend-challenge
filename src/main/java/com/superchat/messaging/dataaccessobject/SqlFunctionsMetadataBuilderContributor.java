package com.superchat.messaging.dataaccessobject;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.BooleanType;

public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor
{
    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction("searchContactNames",
            new SQLFunctionTemplate(
                BooleanType.INSTANCE,
                "to_tsvector(name) @@ plainto_tsquery(?1)"));
    }
}
