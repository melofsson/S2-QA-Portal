package se.soprasteria.s2qaportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import se.soprasteria.s2qaportal.model.TestCase;
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

        TestCase testCase1 = new TestCase("storeStartRentedSection","Error when trying to find an element by text [ Thriller ]","DemoBrowser");

        TestCase testCase2 = new TestCase("storeAssetDetailForEpisodeLoggedOut","Error when trying to find an element by text [ Tv-serier ]","DemoBrowser");

        TestCase testCase3 = new TestCase("searchOverlay","Example logMessage of a test 3 that has never existed","DemoBrowser");
        TestCase testCase4 = new TestCase("storeProviderFilterLoggedOut","Example logMessage of a test 4 that has never existed","DemoBrowser");

        testCase1.setFullName("telia.playplus.crossplatform.videoOnDemand.store.StoreStart.storeStartRentedSection");
        testCase2.setFullName("telia.playplus.crossplatform.videoOnDemand.store.StoreAssetDetailForEpisode.storeAssetDetailForEpisodeLoggedOut");
        testCase3.setFullName("telia.playplus.crossplatform.search.SearchFunctions.searchOverlay");
        testCase4.setFullName("telia.playplus.crossplatform.videoOnDemand.store.StoreProviderFilter.storeProviderFilterLoggedOut");

        testRepository.save(testCase1);
        testRepository.save(testCase2);
        testRepository.save(testCase3);
        testRepository.save(testCase4);
    }

}
