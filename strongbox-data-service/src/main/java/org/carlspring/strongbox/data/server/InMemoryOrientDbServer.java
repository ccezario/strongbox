package org.carlspring.strongbox.data.server;

import javax.inject.Inject;

import org.carlspring.strongbox.config.ConnectionConfig;
import org.carlspring.strongbox.config.ConnectionConfigOrientDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.sql.OCommandSQL;

@Component
@Conditional(InMemoryOrientDbServer.class)
public class InMemoryOrientDbServer implements OrientDbServer, Condition
{

    private static final Logger logger = LoggerFactory.getLogger(InMemoryOrientDbServer.class);

    @Inject
    private ConnectionConfig connectionConfig;

    @Override
    public void start()
    {
        String database = connectionConfig.getDatabase();
        logger.info(String.format("Initialize In-Memory OrientDB server for [%s]", database));

        ODatabaseDocumentTx tx = new ODatabaseDocumentTx(connectionConfig.getUrl());
        if (!tx.exists())
        {
            logger.info(String.format("Create database [%s]", connectionConfig.getDatabase()));
            tx.create();

            OCommandSQL cmd = new OCommandSQL("UPDATE ouser SET password = :password WHERE name = :name");
            Integer result = tx.command(cmd).execute(connectionConfig.getPassword(), connectionConfig.getUsername());
            if (result.compareTo(1) < 0) {
                //TODO: ADD new user
                logger.info(String.format("Add new OrientDB user [%s] for [%s]", connectionConfig.getUsername(), connectionConfig.getDatabase()));
            }
            tx.commit();
        }
        tx.close();

    }

    @Override
    public void stop()
    {

    }

    @Override
    public boolean matches(ConditionContext conditionContext,
                           AnnotatedTypeMetadata metadata)

    {
        return ConnectionConfigOrientDB.resolveProtocol(conditionContext.getEnvironment()).equals("memory");
    }

}
