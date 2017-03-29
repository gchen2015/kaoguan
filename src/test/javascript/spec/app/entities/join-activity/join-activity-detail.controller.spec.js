'use strict';

describe('Controller Tests', function() {

    describe('JoinActivity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJoinActivity, MockUser, MockActivity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJoinActivity = jasmine.createSpy('MockJoinActivity');
            MockUser = jasmine.createSpy('MockUser');
            MockActivity = jasmine.createSpy('MockActivity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'JoinActivity': MockJoinActivity,
                'User': MockUser,
                'Activity': MockActivity
            };
            createController = function() {
                $injector.get('$controller')("JoinActivityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kaoguanApp:joinActivityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
