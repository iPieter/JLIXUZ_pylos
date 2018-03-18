package be.kuleuven.pylos.util;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * @author Pieter
 * @version 1.0
 */
public class KnowledgeSessionHelper
{

    public static KieContainer createRuleBase() {
        KieServices  kieServices  = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        return kieContainer;
    }

    public static StatelessKieSession getStatelessKnowledgeSession(
            KieContainer kieContainer,
            String sessionName) {

        StatelessKieSession kieSession = kieContainer.newStatelessKieSession();

        return kieSession;
    }

    public static KieSession getStatefulKnowledgeSession(
            KieContainer kieContainer,
            String sessionName) {

        KieSession kieSession = kieContainer.newKieSession(sessionName);

        return kieSession;
    }


}
