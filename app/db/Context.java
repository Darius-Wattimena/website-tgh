package db;

import akka.actor.*;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;

import javax.inject.Inject;

public class Context implements ExecutionContextExecutor {
        private final ExecutionContext executionContext;
        private static final String name = "database.dispatcher";

        @Inject
        public Context(ActorSystem actorSystem) {
            this.executionContext = actorSystem.dispatchers().lookup(name);
        }

        @Override
        public ExecutionContext prepare() {
            return executionContext.prepare();
        }

        @Override
        public void execute(Runnable command) {
            executionContext.execute(command);
        }

        @Override
        public void reportFailure(Throwable cause) {
            executionContext.reportFailure(cause);
        }
    }
