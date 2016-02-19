/* Copyright (c) 2016 Nouveautech Limited. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Nouveautech Limited ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Nouveautech Limited.
 *
 * NOUVEAUTECH LIMITED MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. NOUVEAUTECH LIMITED SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/


package com.micro.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Application configuration properties.
 *
 * @author saikris
 */
@Data
@Component
@ConfigurationProperties("resolver")
public class AppProperties {
    private String ldapUrl;
    private String ldapUser;
    private String ldapPassword;
    private String ldapBase;
}
