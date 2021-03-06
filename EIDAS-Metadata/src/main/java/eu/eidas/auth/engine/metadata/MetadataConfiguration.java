/*
 * Copyright (c) 2017 by European Commission
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * This product combines work with different licenses. See the
 * "NOTICE" text file for details on the various modules and licenses.
 * The "NOTICE" text file is part of the distribution.
 * Any derivative works that you distribute must include a readable
 * copy of the "NOTICE" text file.
 */
package eu.eidas.auth.engine.metadata;

import eu.eidas.auth.engine.metadata.impl.EidasMetadataParameters;
import eu.eidas.auth.engine.metadata.impl.EidasMetadataRoleParameters;

/**
 * This is a factory for {@link EidasMetadataParametersI} objects.
 * Now it is simply returning the default implemented object. In the future there there is a possible change in the design:
 * - have two implementations, on in Metadata service, another one in the dependent module(s). The first is similar to
 *   the as-is, it just returns with an object, but in the dependents it could invoke a proxy operation. It depends
 *   on how the Metadata service is designed.
 */
public class MetadataConfiguration {

    public static EidasMetadataParameters newParametersInstance() {
        return new EidasMetadataParameters();
    }

    public static EidasMetadataRoleParameters newRoleParametersInstance() {
        return new EidasMetadataRoleParameters();
    }

}
