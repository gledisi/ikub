jQuery('.showDate').datetimepicker({
	format: 'YYYY-MM-DD H:mm'
});
jQuery('.movieDate').datetimepicker({
	format: 'YYYY-MM-DD'
});

var editModalHall = function(name,id) {
	jQuery('.hallName').val(name);
	jQuery('#editHall\\:hallId').val(id);
	jQuery('#editHall').modal('toggle');
		
}

var editModalMonitor = function(name,hallId,id) {
	jQuery('.monitorName').val(name);
	jQuery('#editMonitor\\:hallId').val(hallId);
	jQuery('#editMonitor\\:monitorId').val(id);
	jQuery('#editMonitor').modal('toggle');
		
}