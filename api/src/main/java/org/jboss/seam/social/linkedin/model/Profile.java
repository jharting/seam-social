/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.social.linkedin.model;

import org.jboss.seam.social.oauth.UserProfile;

/**
 * Implementation of this interface will contain a LinkedInRelated Profile
 *
 * @author Antoine Sabot-Durand
 */
public interface Profile extends UserProfile {

    /**
     * @return the Headline (position) of the profile
     */
    public String getHeadline();

    /**
     * @return the Lastname of the profile
     */
    public String getLastName();

    /**
     * @return the Firstname of the profile
     */
    public String getFirstName();

    /**
     * @return the public URL to access this profile on LinkedInRelated
     */
    public String getStandardProfileUrl();

}
