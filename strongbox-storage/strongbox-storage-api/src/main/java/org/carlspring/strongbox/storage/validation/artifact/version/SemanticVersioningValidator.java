package org.carlspring.strongbox.storage.validation.artifact.version;

import org.carlspring.strongbox.artifact.coordinates.ArtifactCoordinates;
import org.carlspring.strongbox.storage.repository.Repository;
import org.carlspring.strongbox.storage.validation.artifact.ArtifactCoordinatesValidatorRegistry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.semver.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SemanticVersioningValidator implements VersionValidator
{

    private static final Logger logger = LoggerFactory.getLogger(SemanticVersioningValidator.class);


    public static final String ALIAS = "semantic-version-validator";

    public static final String DESCRIPTION = "Semantic version validator";

    @Inject
    private ArtifactCoordinatesValidatorRegistry artifactCoordinatesValidatorRegistry;


    @PostConstruct
    @Override
    public void register()
    {
        artifactCoordinatesValidatorRegistry.addProvider(ALIAS, this);

        logger.info("Registered artifact coordinates validator '" + getClass().getCanonicalName() +"'" +
                    " with alias '" + ALIAS + "'.");
    }

    @Override
    public String getAlias()
    {
        return ALIAS;
    }

    @Override
    public String getDescription()
    {
        return DESCRIPTION;
    }

    @Override
    public boolean supports(Repository repository)
    {
        return repository.getArtifactCoordinateValidators().contains(ALIAS);
    }

    @Override
    public void validate(Repository repository,
                         ArtifactCoordinates coordinates)
            throws VersionValidationException
    {
        String version = coordinates.getVersion();
        try
        {
            Version.parse(version);
        }
        catch (IllegalArgumentException e)
        {
            throw new VersionValidationException(String.format("Artifact version [%s] should follow the Semantic " +
                                                               "Versioning specification (https://semver.org/).",
                                                               version));
        }
    }

}
