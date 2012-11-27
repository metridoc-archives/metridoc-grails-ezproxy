/*
 * Copyright 2010 Trustees of the University of Pennsylvania Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
    jdbc.batch_size = 5000
}

productionDataSourceProperties = {
    maxActive = 50
    maxIdle = 25
    minIdle = 5
    initialSize = 5
    minEvictableIdleTimeMillis = 60000
    timeBetweenEvictionRunsMillis = 60000
    maxWait = 10000
    validationQuery = "select 1"
}

environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devEzproxy;MVCC=TRUE;LOCK_TIMEOUT=10000"
            logSql = false
        }

        dataSource_admin {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devAdmin;MVCC=TRUE;LOCK_TIMEOUT=10000"
            logSql = true
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devEzproxy;MVCC=TRUE;LOCK_TIMEOUT=10000"
            logSql = true
        }
    }
    production {

        dataSource_admin {
            pooled = true
            dbCreate = "none"
            url = "jdbc:mysql://localhost:3306/admin"
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            username = "root"
            password = "password"
            properties(productionDataSourceProperties)
        }

        dataSource {
            pooled = true
            dbCreate = "none"
            url = "jdbc:mysql://localhost:3306/prod_ezproxy"
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            username = "root"
            password = "password"
            properties(productionDataSourceProperties)
        }
    }
}
