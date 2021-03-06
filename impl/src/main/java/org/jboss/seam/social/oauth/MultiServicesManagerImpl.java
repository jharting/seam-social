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
package org.jboss.seam.social.oauth;


import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.MultiServicesManager;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.RelatedTo;
import org.jboss.seam.social.oauth.Service;

public class MultiServicesManagerImpl implements MultiServicesManager,Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2681869484541158766L;

    @Inject
    @Any
    private Instance<OAuthService> serviceInstances;

    private List<Service> listOfServices;
    
    private Set<OAuthService> services;

    private OAuthService currentService;

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.manager.MultiServicesManager#getListOfServices()
     */
    @Override
    public List<Service> getListOfServices() {
        return listOfServices;
    }

   

   

    public MultiServicesManagerImpl() {
        super();
        services = new HashSet<OAuthService>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.manager.MultiServicesManager#addService(org.jboss.seam.social.oauth.Service)
     */
    @Override
    public OAuthService getNewService(Service serviceEnum) {
        OAuthService service = serviceInstances.select(new RelatedTo.RelatedToLiteral(serviceEnum)).get();
        return service;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.manager.MultiServicesManager#addService(java.lang.String)
     */
    @Override
    public OAuthService getNewService(String serviceName) {
        Service serviceEnum = Service.valueOf(serviceName);
        return getNewService(serviceEnum);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.manager.MultiServicesManager#getServices()
     */
    @Override
    public Set<OAuthService> getServices() {
        return services;
    }


    @PostConstruct
    protected void init() {
        if (listOfServices == null || listOfServices.size() == 0) {
            listOfServices = Arrays.asList(Service.values());
        }
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.manager.MultiServicesManager#addService(org.jboss.seam.social.oauth.OAuthService)
     */
    @Override
    public void addService(OAuthService service) {
       services.add(service);  
    }

    @Override
    public OAuthService getCurrentService() {
        return currentService;
    }

    @Override
    public void setCurrentService(OAuthService currentService) {
        this.currentService = currentService;
    }
    
    @Override
    public boolean isCurrentServiceConnected() {
        return getCurrentService() != null && getCurrentService().isConnected();
    }
    
    @Override
    public void connectCurrentService() {
       getCurrentService().initAccessToken();
       addService(getCurrentService());
    }
    
    @Override
    public String initNewService(Service servType) {
        setCurrentService(getNewService(servType));
        return getCurrentService().getAuthorizationUrl();
    }
    
    
    @Override
    public void destroyCurrentService()
    {
        getServices().remove(getCurrentService());
        getCurrentService().resetConnection();
        setCurrentService(null);
    }

}
