package com.micro.security.rest;

import com.micro.security.model.AccessRule;
import com.micro.security.repository.RuleRepository;
import org.apache.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


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

    @RequestMapping(value = "/rule/{uid}", method = RequestMethod.DELETE, produces = "text/plain")
    public ResponseEntity<String> deleteAccessRule(@PathVariable("uid") final String uid) {
        ruleRepository.delete(uid);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rule", method = RequestMethod.PUT, consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> updateAccessRule(@RequestBody final AccessRule accessRule) {
        ruleRepository.update(accessRule);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/rule/{uid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<AccessRule> ruleById(@PathVariable("uid") final String uid) {
        AccessRule rule = ruleRepository.findById(uid);
        Link link = linkTo(RuleResource.class).slash("rule").slash(rule.getUid()).withSelfRel();
        rule.add(link);
        return new ResponseEntity<AccessRule>(rule, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/rules", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<AccessRule>> allRules() {
        List<AccessRule> allRules = ruleRepository.allAccessRules();
        allRules.forEach(rule -> {
            Link link = linkTo(RuleResource.class).slash("rule").slash(rule.getUid()).withSelfRel();
            rule.add(link);
        });
        return new ResponseEntity<List<AccessRule>>(allRules, HttpStatus.OK);
    }
}
