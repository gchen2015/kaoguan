'use strict';

describe('Controller Tests', function() {

    describe('PreferActivity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPreferActivity, MockUser, MockActivity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPreferActivity = jasmine.createSpy('MockPreferActivity');
            MockUser = jasmine.createSpy('MockUser');
            MockActivity = jasmine.createSpy('MockActivity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PreferActivity': MockPreferActivity,
                'User': MockUser,
                'Activity': MockActivity
            };
            createController = function() {
                $injector.get('$controller')("PreferActivityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kaoguanApp:preferActivityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
