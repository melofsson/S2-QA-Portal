package se.soprasteria.s2qaportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import se.soprasteria.s2qaportal.model.Test;
import se.soprasteria.s2qaportal.repository.TestRepository;

@Component
public class BaseController implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TestRepository testRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData(){
        testRepository.deleteAll();

        Test test1 = new Test("storeStartRentedSection","Error when trying to find an element by text [ Thriller ]","DemoBrowser");

        Test test2 = new Test("storeAssetDetailForEpisodeLoggedOut","Error when trying to find an element by text [ Tv-serier ]","DemoBrowser");

        Test test3 = new Test("searchOverlay","Example logMessage of a test 3 that has never existed","DemoBrowser");
        Test test4 = new Test("storeProviderFilterLoggedOut","Example logMessage of a test 4 that has never existed","DemoBrowser");

        test1.setFullName("telia.playplus.crossplatform.videoOnDemand.store.StoreStart.storeStartRentedSection");
        test2.setFullName("telia.playplus.crossplatform.videoOnDemand.store.StoreAssetDetailForEpisode.storeAssetDetailForEpisodeLoggedOut");
        test3.setFullName("telia.playplus.crossplatform.search.SearchFunctions.searchOverlay");
        test4.setFullName("telia.playplus.crossplatform.videoOnDemand.store.StoreProviderFilter.storeProviderFilterLoggedOut");

        testRepository.save(test1);
        testRepository.save(test2);
        testRepository.save(test3);
        testRepository.save(test4);
    }

}
