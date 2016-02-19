package com.concorde.resolver

import com.micro.security.SecurityTokenApp
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * Created by saikris on 25/01/2016.
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = [SecurityTokenApp])
@WebIntegrationTest(randomPort = true)
@ActiveProfiles("stub")
@Stepwise
public class BaseIntegration extends Specification {

}