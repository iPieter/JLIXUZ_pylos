package be.kuleuven.pylos.util;

import org.kie.api.KieServices;
import org.kie.api.definition.KiePackage;
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

        int nRules = 0;
        for( KiePackage pack: kieSession.getKieBase().getKiePackages() ){
            nRules += pack.getRules().size();
        }

        //System.out.println("RULES: " + nRules);

        return kieSession;
    }


}
