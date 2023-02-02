package com.openfinance.facilitator.core.service;

import com.openfinance.facilitator.core.domain.postman.*;
import com.openfinance.facilitator.core.ports.OpenFinanceParticipantPort;
import com.openfinance.facilitator.core.ports.OpenFinanceParticipantRestPort;
import com.openfinance.facilitator.core.utils.OpenFinanceFileUtils;
import com.openfinance.facilitator.core.domain.enums.EFileExtension;
import com.openfinance.facilitator.core.domain.participant.OpenFinanceParticipant;
import com.openfinance.facilitator.core.domain.participant.server.ApiDiscoveryEndpoint;
import com.openfinance.facilitator.core.exceptions.OpenFinanceException;
import com.openfinance.facilitator.core.utils.OpenFinanceUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public final class OpenFinanceParticipantServiceImp implements OpenFinanceParticipantPort {

    @Autowired
    private OpenFinanceParticipantRestPort openFinanceParticipantRestPort;

    private static String PC_FILE_NAME = "%s-openfinancebrazil-participants";

    @Override
    /**
     * <p>Method responsible for fetching the list of OpenFinance Brazil participants and generating a file type Postman Collection.</>
     */
    public File generatePostmanCollectionFile() throws OpenFinanceException {
        try {
            final var participants = openFinanceParticipantRestPort.getAll();

            // Using TreeMap to sort map values by key
            final var mapByApiFamilyType =  new TreeMap<>(groupingByApiFamilyType(participants));

            List<PostmanItem> items = new ArrayList<>();
            for (Map.Entry<String, List<PostmanSubItem>> entry : mapByApiFamilyType.entrySet()) {
                items.add(createPostmanItem(entry.getKey(), entry.getValue()));
            }

            final var postmanObject = createPostman(items);
            final var postmanCollectionFileName = getPostmanCollectionFileName();

            return OpenFinanceFileUtils.createTempFileFromObject(postmanCollectionFileName, EFileExtension.JSON, postmanObject);

        }catch (final OpenFinanceException openFinanceException) {
            throw openFinanceException;
        }catch (final Throwable throwable){
            log.error("Error generating post collection. {}", throwable);
            throw new OpenFinanceException("Error generating post collection.");
        }
    }

    /**
     * Grouping by ApiResource::getApiFamilyType
     *
     * @param participants
     * @return Map<String, List < OpenFinanceParticipant>>
     */
    private Map<String, List<PostmanSubItem>> groupingByApiFamilyType(final List<OpenFinanceParticipant> participants) {
        Map<String, List<PostmanSubItem>> mapByApiFamilyType = new HashMap<>();

        participants.forEach(openFinanceParticipant -> {
            final var organizationName = openFinanceParticipant.getOrganisationName();

            openFinanceParticipant.getServers().forEach(authorizationServer ->
                authorizationServer.getResources().forEach(apiResource -> {
                    final var apiFamilyType = apiResource.getApiFamilyType();

                    if(!mapByApiFamilyType.containsKey(apiFamilyType))
                        mapByApiFamilyType.put(apiFamilyType, new ArrayList<>());

                    mapByApiFamilyType.get(apiFamilyType).addAll(createPostmanSubItems(organizationName, apiResource.getEndpoints()));

                }));
        });

        return mapByApiFamilyType;
    }

    private List<PostmanSubItem> createPostmanSubItems(final String ornazinationName, final List<ApiDiscoveryEndpoint> apiDiscoveryEndpoints) {

        List<PostmanSubItem> subItems = new ArrayList<>();
        apiDiscoveryEndpoints.forEach(apiDiscoveryEndpoint -> {

            try{
                final var endPoint =  apiDiscoveryEndpoint.getEndPoint();
                final var protocol = OpenFinanceUrlUtils.getProtocolByURL(endPoint);
                final var splittedHost = OpenFinanceUrlUtils.getHostByURL(endPoint);
                final var splittedPath = OpenFinanceUrlUtils.getPathByURL(endPoint);

                final var subItem =
                    PostmanSubItem.builder()
                        .name(ornazinationName.toUpperCase())
                        .request(PostmanRequest.builder()
                            .url(PostmanUrl.builder()
                                .raw(endPoint)
                                .protocol(protocol)
                                .host(splittedHost)
                                .path(splittedPath)
                                .build())
                            .build())
                        .build();

                subItems.add(subItem);

            }catch (final OpenFinanceException ex) {
               log.error("Error generating endpoint information for postman collection. endpoint={}, error={}",
                       apiDiscoveryEndpoint.getEndPoint(), ex.getMessage());
            }
        });

        return subItems;
    }

    /**
     * <p>Method responsible for creating an object of type {@link PostmanItem}</>
     * @param apiFamilyType
     * @param postmanSubItems
     * @return {@link PostmanItem}
     */
    private PostmanItem createPostmanItem(final String apiFamilyType, final List<PostmanSubItem> postmanSubItems){
        // Sorting list by organization name
        final var sortedList =
            postmanSubItems.stream()
                    .sorted(Comparator.comparing(PostmanSubItem::getName))
                    .collect(Collectors.toList());

        // Creating PostmanItem
        return PostmanItem.builder()
                .name(apiFamilyType)
                .subItems(sortedList)
                .build();
    }

    /**
     * <p>Method responsible for creating an object of type {@link Postman} </>
     * @param items
     * @return {@link Postman}
     */
    private Postman createPostman(final List<PostmanItem>  items){
       return  Postman.builder()
                .info(PostmanInfo.builder().build())
                .items(items)
                .build();
    }

    /**
     * <p> Method responsible for create a postman collection file name.</>
     * @return
     */
    private String getPostmanCollectionFileName(){
        final var sdf = new SimpleDateFormat("MM-dd-yyyy");
        return String.format(PC_FILE_NAME, sdf.format(new Date()));
    }
}
