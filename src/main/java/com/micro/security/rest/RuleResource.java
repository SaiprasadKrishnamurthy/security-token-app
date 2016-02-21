package com.micro.security.rest;

import com.micro.security.model.AccessRule;
import com.micro.security.repository.RuleRepository;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by saipkri on 19/02/16.
 */
@RestController
public class RuleResource {

    private static final Logger LOG = Logger.getLogger(RuleResource.class);

    private final RuleRepository ruleRepository;

    @Inject
    public RuleResource(final RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @RequestMapping(value = "/rule", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> createAccessRule(@RequestBody final AccessRule accessRule) {
        ruleRepository.save(accessRule);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/rules", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<AccessRule>> allRules() {
        return new ResponseEntity<List<AccessRule>>(ruleRepository.allAccessRules(), HttpStatus.OK);
    }
}
